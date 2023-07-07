//package ru.git.lab.bot.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = MergeRequestController.class)
//public class MergeRequestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MergeRequestController mergeRequestController;
//
//    @Test
//    public void shouldReturnOk() throws Exception {
//        String request = readFileAsString("src/test/resources/merge_request.json");
//
//
//
//        mockMvc.perform(post("/merges/requests").content(objectMapper.writeValueAsString(request))
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    private String readFileAsString(String file)throws Exception
//    {
//        return new String(Files.readAllBytes(Paths.get(file)));
//    }
//
//}
