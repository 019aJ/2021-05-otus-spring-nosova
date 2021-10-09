package ru.otus.springintegration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.support.PeriodicTrigger;
import ru.otus.springintegration.domain.Person;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
@Slf4j
public class SpringIntegrationApplication {

    private static final String[] manNames = {"Иван", "Артем", "Александр", "Владимир", "Сергей", "Валентин", "Станислав", "Юрий", "Кирилл", "Павел"};
    private static final String[] womanNames = {"Анна", "Юлия", "Елена", "Ирина", "Наталия", "Татьяна", "Людмила", "Надежда", "Ульяна", "Екатерина"};

    private static List<Person> generateOrderItems() {
        Person rndPerson = RandomUtils.nextBoolean() ? new Person(manNames[RandomUtils.nextInt(0, 10)], Person.GENDER.MAN) : new Person(womanNames[RandomUtils.nextInt(0, 10)], Person.GENDER.WOMAN);
        return Arrays.asList(new Person(manNames[RandomUtils.nextInt(0, 10)], Person.GENDER.MAN),
                new Person(womanNames[RandomUtils.nextInt(0, 10)], Person.GENDER.WOMAN),
                new Person(womanNames[RandomUtils.nextInt(0, 10)], Person.GENDER.WOMAN),
                new Person(manNames[RandomUtils.nextInt(0, 10)], Person.GENDER.MAN),
                rndPerson
        );
    }

    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SpringIntegrationApplication.class);
        QueueChannel inputChanel = ctx.getBean("peopleChannel", QueueChannel.class);
        while (true) {
            log.info("Следующий набор");
            List<Person> persons = generateOrderItems();
            inputChanel.send(MessageBuilder.withPayload(persons).build());
            Thread.sleep(10000);
        }
    }

    @Bean
    public QueueChannel peopleChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel resultChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel statisticChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow lifeFlow() {
        return IntegrationFlows.from("peopleChannel")
                .split()
                .handle("childhood", "babyTime")
                .publishSubscribeChannel(c -> c.subscribe(s ->
                        s.handle(m -> sleepUninterruptibly(1, TimeUnit.SECONDS))))
                .handle("childhood", "schoolTime")
                .wireTap(supportiveCommentFlow())
                .<Person, Boolean>route(p -> p.getGender() == Person.GENDER.MAN && RandomUtils.nextInt(0, 100) < 15,
                        mapping -> mapping.subFlowMapping(true, sf -> sf.<Person, Person>transform(this::transformToArmyMan
                        )).subFlowMapping(false, sf -> sf.<Person, Person>transform(p -> p)))
                .<Person, Boolean>route(p -> RandomUtils.nextInt(0, 100) < 50,
                        mapping -> mapping.subFlowMapping(true, sf -> sf.handle("childhood", "universityTime"))
                                .subFlowMapping(false, sf -> sf.<Person, Person>transform(p -> p)))
                .aggregate()
                .handle("adultLife", "privateLife")
                .split()
                .<Person, Boolean>route(p -> p.isMarried() && RandomUtils.nextInt(0, 100) < 50,
                        mapping -> mapping.subFlowMapping(true, sf -> sf.handle("adultLife", "divorce"))
                                .subFlowMapping(false, sf -> sf.<Person, Person>transform(p -> p)))
                .<Person, Boolean>route(p -> RandomUtils.nextInt(0, 100) < 20,
                        mapping -> mapping.subFlowMapping(true, sf -> sf.handle("adultLife", "sacking"))
                                .subFlowMapping(false, sf -> sf.<Person, Person>transform(p -> p)))
                .wireTap(supportiveCommentFlow())
                .handle("adultLife", "retire")
                .wireTap(supportiveCommentFlow())
                .aggregate()
                .wireTap(supportiveCommentFlow())
                .channel("resultChannel")
                .get();
    }

    @Bean
    public IntegrationFlow fileWriter() {
        return IntegrationFlows.from("resultChannel")
                .handle("revision", "revise")
                .handle(targetDirectory())
                .get();
    }

    @Bean
    public IntegrationFlow anotherFileWriter() {
        return IntegrationFlows.from("resultChannel")
                .handle("revision", "revise")
                .handle(x -> log.info(x.getPayload().toString()))
                .get();
    }

    @Bean
    public IntegrationFlow statisticWriter() {
        return IntegrationFlows.from("resultChannel")
                .split()
                .aggregate(aggregator -> aggregator
                        .correlationStrategy(m -> ((Person) m.getPayload()).getHappiness() <= 100))
                .handle("revision", "reviseHappiness")
                .filter(x -> x.toString().length() > 0)
                .handle(statisticTargetDirectory())
                .get();
    }

    @Bean
    public MessageHandler statisticTargetDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("result"));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        handler.setFileNameGenerator(m ->
                "Статистика-" + new SimpleDateFormat("dd-MM-YYYY HH-mm-ss").format(new Date()) + ".log");
        return handler;
    }


    @Bean
    public MessageHandler targetDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("result"));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        handler.setFileNameGenerator(m ->
                "Выгрузка-" + new SimpleDateFormat("dd-MM-YYYY HH-mm-ss").format(new Date()) + ".log");
        return handler;
    }

    @Bean
    public IntegrationFlow supportiveCommentFlow() {
        return f -> f.log(m -> {
            if (m.getPayload() instanceof Person) {
                Person p = (Person) m.getPayload();
                if (p.getHappiness() <= 100) {
                    String state = p.getGender() == Person.GENDER.MAN ? " приуныл" : " приуныла";
                    return p.getName() + state;
                }
                String state = p.getGender() == Person.GENDER.MAN ? " счастлив" : " счастлива";
                return p.getName() + " счастлив";
            } else if (m.getPayload() instanceof List) {
                return "Агрегация завершена";
            }
            return "";
        });
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10));
        return pollerMetadata;
    }

    private Person transformToArmyMan(Person p) {
        Person afterArmy = new Person(p.getName(), p.getGender(), p.getAchievements());
        p.setAge(p.getAge() + 1);
        p.setHappiness(p.getHappiness() + RandomUtils.nextInt(0, 20) - 15);
        p.getAchievements().add("Служил в армии");
        log.info(p.getName() + " cлужил в армии");
        return p;
    }

}
