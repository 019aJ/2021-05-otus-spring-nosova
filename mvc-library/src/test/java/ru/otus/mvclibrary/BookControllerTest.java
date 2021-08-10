package ru.otus.mvclibrary;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import ru.otus.mvclibrary.controllers.BookController;
import ru.otus.mvclibrary.dto.BookDTO;
import ru.otus.mvclibrary.models.Author;
import ru.otus.mvclibrary.models.Genre;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@DisplayName("Тест для книг ")
@ComponentScan({"ru.otus.mvclibrary.repositories", "ru.otus.mvclibrary.controllers"})
@ExtendWith(SpringExtension.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookController bookController;

    @Test
    public void testList() throws Exception {
        when(bookController.listPage(any())).thenAnswer(x -> {
            Object arg1 = x.getArgument(0);
            if (arg1 instanceof Model) {
                var result = Arrays.asList(new BookDTO(1L, "test1", new Author("123", "123"), new Genre("123")));
                ((Model) arg1).addAttribute("books", result);
            }
            return "list";
        });

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(content().string(Matchers.containsString("test1")))
                .andExpect(content().string(Matchers.containsString("123")));
    }

    @Test
    public void testEditGet() throws Exception {

        when(bookController.editPage(any(), any())).thenAnswer(x -> {
            Object arg1 = x.getArgument(1);
            if (arg1 instanceof Model) {
                ((Model) arg1).addAttribute("book", new BookDTO(1L, "test1", new Author("123", "123"), new Genre("123")));
                ((Model) arg1).addAttribute("authors", Arrays.asList(new Author(1L, "444", "444")));
                ((Model) arg1).addAttribute("genres", Arrays.asList(new Genre(2L, "555")));
            }

            return "edit";
        });

        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(status().isOk()).andExpect(view().name("edit"))
                .andExpect(content().string(Matchers.containsString("test1")))
                .andExpect(content().string(Matchers.containsString("444")))
                .andExpect(content().string(Matchers.containsString("555")));
    }

    @Test
    public void testAddGet() throws Exception {

        when(bookController.addBook(any())).thenAnswer(x -> {
            Object arg1 = x.getArgument(0);
            if (arg1 instanceof Model) {
                ((Model) arg1).addAttribute("book", new BookDTO());
                ((Model) arg1).addAttribute("authors", Arrays.asList(new Author(1L, "444", "444")));
                ((Model) arg1).addAttribute("genres", Arrays.asList(new Genre(2L, "555")));
            }

            return "add";
        });

        mvc.perform(get("/add"))
                .andExpect(status().isOk()).andExpect(view().name("add"))
                .andExpect(content().string(Matchers.containsString("444")))
                .andExpect(content().string(Matchers.containsString("555")));
    }

}
