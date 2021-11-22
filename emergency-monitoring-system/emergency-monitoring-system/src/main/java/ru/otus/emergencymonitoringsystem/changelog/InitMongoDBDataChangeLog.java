package ru.otus.emergencymonitoringsystem.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.emergencymonitoringsystem.models.*;
import ru.otus.emergencymonitoringsystem.repositories.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "onosova", runAlways = false)
    public void dropDB(MongoDatabase database) {
        database.getCollection("ACL").drop();
        database.getCollection("Areas").drop();
        database.getCollection("ContaminationAreas").drop();
        database.getCollection("EmergencyMonitoring").drop();
        database.getCollection("EmergencyTypes").drop();
        database.getCollection("Materials").drop();
        database.getCollection("Users").drop();
    }

    @ChangeSet(order = "001", id = "initEmergencies", author = "onosova", runAlways = false)
    public void initEmergencies(ContaminationAreaRepository contaminationAreaRepository,
                                EmergencyTypeRepository emergencyTypeRepository,
                                MaterialRepository materialRepository,
                                WaterAreaRepository waterAreaRepository,
                                EmergencyMonitoringRepository emergencyMonitoringRepository) throws ParseException {

        Material oilLight = new Material(null, "Нефть легкая", "НЕФТЬ ЛЕГКАЯ", 0.85);
        Material oilMiddle = new Material(null, "Нефть средняя", "НЕФТЬ СРЕДНЯЯ", 0.9);
        Material oilHeavy = new Material(null, "Нефть тяжёлая ", "НЕФТЬ ТЯЖЁЛАЯ", 0.98);
        oilLight = materialRepository.save(oilLight);
        oilMiddle = materialRepository.save(oilMiddle);
        oilHeavy = materialRepository.save(oilHeavy);

        EmergencyType emergencyType1 = new EmergencyType(null, "Выброс", "ВЫБРОС");
        EmergencyType emergencyType2 = new EmergencyType(null, "Пожар", "ПОЖАР");
        emergencyType1 = emergencyTypeRepository.save(emergencyType1);
        emergencyType2 = emergencyTypeRepository.save(emergencyType2);

        WaterArea area1 = new WaterArea(null, "Каспийское море", "КАСПИЙСКОЕ МОРЕ", null);
        WaterArea area2 = new WaterArea(null, "Балтийское море", "БАЛТИЙСКОЕ МОРЕ", null);

        double[][] coords = new double[][]
                {{143.02002, 54.149567}, {142.932129, 54.007769}, {142.866211, 53.839564}, {143.10791, 53.592505}, {143.305664, 53.265213}, {143.349609, 52.855864}, {143.327637, 52.562995}, {143.151855, 52.241256}, {143.305664, 51.822198}, {143.426514, 51.570241}, {143.492432, 51.382067}, {143.504791, 51.281676}, {143.556976, 51.166428}, {143.602295, 51.015483}, {143.800049, 50.362985}, {144.008789, 50.039502}, {144.135132, 49.763526}, {144.146118, 49.624946}, {144.261475, 49.51451}, {144.343872, 49.199654}, {144.420776, 49.030665}, {146.288452, 49.113434}, {147.370605, 54.863963}, {143.4375, 55.153766}, {142.866211, 54.406143}, {142.866211, 54.406143}, {143.02002, 54.149567}};
        Polygon seeCoord = new Polygon(Arrays.stream(coords).map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
        WaterArea area3 = new WaterArea(null, "Сахалинский шельф", "САХАЛИНСКИЙ ШЕЛЬФ", seeCoord);
        WaterArea area4 = new WaterArea(null, "Печорское море", "ПЕЧОРСКОЕ МОРЕ", null);
        area1 = waterAreaRepository.save(area1);
        area2 = waterAreaRepository.save(area2);
        area3 = waterAreaRepository.save(area3);
        area4 = waterAreaRepository.save(area4);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        LocalDateTime currentDate = LocalDateTime.now();
        currentDate = currentDate.minus(1, ChronoUnit.HOURS);
        Instant timeOnEpochDayInDefaultTimeZone = currentDate.atZone(ZoneId.systemDefault())
                .toInstant();
        Date monitoringDate = Date.from(timeOnEpochDayInDefaultTimeZone);
        EmergencyMonitoring emergency2 = EmergencyMonitoring.builder().emergencyType(emergencyType1).material(oilLight)
                .waterArea(area4).createDate(simpleDateFormat.parse("22.01.2021 21:41"))
                .finishDate(simpleDateFormat.parse("23.01.2021 12:58"))
                .isActive(false).build();
        emergency2 = emergencyMonitoringRepository.save(emergency2);

        EmergencyMonitoring emergency3 = EmergencyMonitoring.builder().emergencyType(emergencyType1).material(oilHeavy)
                .waterArea(area1).createDate(simpleDateFormat.parse("22.05.2021 21:41"))
                .isActive(false).finishDate(simpleDateFormat.parse("23.05.2021 12:58")).build();
        emergency3 = emergencyMonitoringRepository.save(emergency3);

        EmergencyMonitoring emergency4 = EmergencyMonitoring.builder().emergencyType(emergencyType1).material(oilMiddle)
                .waterArea(area2).createDate(simpleDateFormat.parse("22.06.2021 21:41"))
                .isActive(false).finishDate(simpleDateFormat.parse("23.06.2021 12:58")).build();
        emergency4 = emergencyMonitoringRepository.save(emergency4);

        coords = new double[][]{{140.549855, 47.76082}, {140.555863, 47.757849}, {140.555477, 47.751472}, {140.54625, 47.749798}, {140.540757, 47.754413}, {140.542946, 47.758945}, {140.549855, 47.76082}};
        Polygon poly1 = new Polygon(Arrays.stream(coords).map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));

        EmergencyMonitoring emergencyMonitoring = EmergencyMonitoring.builder()
                .emergencyType(emergencyType1).material(oilLight).waterArea(area3)
                .createDate(new Date()).finishDate(new Date())
                .isActive(false)
                .initialCoordinates(poly1).build();
        emergencyMonitoring = emergencyMonitoringRepository.save(emergencyMonitoring);

        coords = new double[][]
                {{143.661346, 51.412698}, {143.660874, 51.411828}, {143.65948, 51.411226}, {143.657269, 51.411199}, {143.655617, 51.411868}, {143.654523, 51.412604}, {143.654201, 51.413769}, {143.655252, 51.414639}, {143.656497, 51.415013}, {143.657227, 51.415134}, {143.658857, 51.41512}, {143.660316, 51.414531}, {143.661389, 51.413595}, {143.661346, 51.412698}};
        poly1 = new Polygon(Arrays.stream(coords).map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));

        EmergencyMonitoring emergencyMonitoring2 = EmergencyMonitoring.builder().emergencyType(emergencyType1).material(oilLight).waterArea(area3).createDate(monitoringDate)
                .isActive(true)
                .initialCoordinates(poly1).build();
        emergencyMonitoring = emergencyMonitoringRepository.save(emergencyMonitoring2);

        double xShift = -0.02;
        double yShift = -0.01;
        updateCoord(coords, xShift, yShift);
        Polygon poly2 = new Polygon(Arrays.stream(coords).map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
        updateCoord(coords, xShift, yShift);
        Polygon poly3 = new Polygon(Arrays.stream(coords).map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
        updateCoord(coords, xShift, yShift);
        Polygon poly4 = new Polygon(Arrays.stream(coords).map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
        updateCoord(coords, xShift, yShift);
        Polygon poly5 = new Polygon(Arrays.stream(coords).map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));

        ContaminationArea contaminationArea1 = ContaminationArea.builder()
                .createDate(monitoringDate)
                .emergencyMonitoringId(emergencyMonitoring.getId())
                .coordinates(Arrays.asList(poly1, poly2, poly3, poly4))
                .period(60)
                .dataType(new DataType("Прогноз"))
                .build();
        contaminationAreaRepository.save(contaminationArea1);
        ContaminationArea contaminationArea2 = ContaminationArea.builder()
                .createDate(new Date())
                .emergencyMonitoringId(emergencyMonitoring.getId())
                .coordinates(Arrays.asList(poly2, poly3, poly4, poly5))
                .period(60)
                .dataType(new DataType("Прогноз"))
                .build();
        contaminationAreaRepository.save(contaminationArea2);
    }

    @ChangeSet(order = "002", id = "initUsers", author = "onosova", runAlways = true)
    public void initUsers(EMSUserRepository userRepository) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        EMSUser user = new EMSUser(null, "admin", encoder.encode("admin"), "ADMIN;EDITOR");
        String userPas = encoder.encode("user");
        EMSUser user2 = new EMSUser(null, "user", userPas, "READER");
        userRepository.save(user);
        userRepository.save(user2);

        EMSUser userArea1 = new EMSUser(null, "user_km", userPas, "READER");
        EMSUser userArea2 = new EMSUser(null, "user_bm", userPas, "READER");
        EMSUser userArea3 = new EMSUser(null, "user_ss", userPas, "READER");
        EMSUser userArea4 = new EMSUser(null, "user_pm", userPas, "READER");

        userRepository.save(userArea1);
        userRepository.save(userArea2);
        userRepository.save(userArea3);
        userRepository.save(userArea4);
    }

    private void updateCoord(double[][] coords, double xShift, double yShift) {
        for (int i = 0; i < coords.length; i++) {
            coords[i][0] += xShift;
            coords[i][1] += yShift;
        }
    }
}
