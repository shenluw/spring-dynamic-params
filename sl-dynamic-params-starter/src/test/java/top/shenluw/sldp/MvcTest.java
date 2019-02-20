package top.shenluw.sldp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 12:22
 */
@WebMvcTest
@ExtendWith(SpringExtension.class)
class MvcTest {

    @Autowired
    private WebApplicationContext context;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/1")
                .param("name", "test name")
                .param("age", "12")
                .param("sldp", Animal.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"clazz\":\"top.shenluw.sldp.Animal\",\"obj\":{\"name\":\"test name\",\"age\":12}}"))
                .andDo(print());
    }

    @Test
    void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/1")
                .param("name", "test name")
                .param("age", "12")
                .param("sldp", Dog.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"clazz\":\"top.shenluw.sldp.Dog\",\"obj\":{\"name\":\"test name\",\"age\":12}}"))
                .andDo(print());
    }

    @Test
    void test3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/1")
                .param("name", "test name")
                .param("age", "12")
                .param("face", "mm")
                .param("sldp", Dog.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"clazz\":\"top.shenluw.sldp.Dog\",\"obj\":{\"name\":\"test name\",\"age\":12,\"face\":\"mm\"}}"))
                .andDo(print());
    }

    @Test
    void test4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/1")
                .param("name", "test name")
                .param("age", "12")
                .param("face", "mm")
                .param("speed", "sp")
                .param("sldp", Cat.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"clazz\":\"top.shenluw.sldp.Cat\",\"obj\":{\"name\":\"test name\",\"age\":12,\"speed\":\"sp\"}}"))
                .andDo(print());
    }


    @Test
    void test5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/1")
                .param("name", "test name")
                .param("age", "12")
                .param("face", "mm")
                .param("speed", "sp")
                .param("height", "11")
                .param("sldp", BDog.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"clazz\":\"top.shenluw.sldp.BDog\",\"obj\":{\"name\":\"test name\",\"age\":12,\"face\":\"mm\",\"speed\":\"sp\",\"height\":\"11\"}}"))
                .andDo(print());
    }

    @Test
    void test6() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/3")
                .param("name", "test name")
                .param("age", "12")
                .param("face", "mm")
                .param("speed", "sp")
                .param("height", "11")
                .param("sldp", BDog.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    void test7() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/2")
                .param("name", "test name")
                .param("age", "12")
                .param("face", "mm")
                .param("speed", "sp")
                .param("height", "11")
                .param("a", BDog.class.getName())
                .param("b", Cat.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"clazz\":\"top.shenluw.sldp.BDog\",\"obj\":{\"name\":\"test name\",\"age\":12,\"face\":\"mm\",\"speed\":\"sp\",\"height\":\"11\"}},{\"clazz\":\"top.shenluw.sldp.Cat\",\"obj\":{\"name\":\"test name\",\"age\":12,\"speed\":\"sp\"}}]"))
                .andDo(print());
    }


    @Test
    void test8() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/4")
                .param("name", "test name")
                .param("sldp", Fly.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"clazz\":\"top.shenluw.sldp.Fly\",\"obj\":{\"name\":\"test name\"}}"))
                .andDo(print());
    }

    @Test
    void test9() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/5")
                .param("sldpJson", "{\"name\":\"test name\",\"age\":12}")
                .param("sldp", Dog.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"clazz\":\"top.shenluw.sldp.Dog\",\"obj\":{\"name\":\"test name\",\"age\":12}}"))
                .andDo(print());
    }

    @Test
    void test10() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/6")
                .param("sldpJson", "{\"name\":\"test name\",\"age\":12,\"speed\":\"test\"}")
                .param("sldp", Dog.class.getName())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(400))
                .andDo(print());
    }
}