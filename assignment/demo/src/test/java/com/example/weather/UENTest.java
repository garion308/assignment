package com.example.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest 
@AutoConfigureMockMvc
public class UENTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testBusinessUen() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "12345678A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("12345678A"))
               .andExpect(jsonPath("$.entityType").value("Business"));
    }

    @Test
    void testLocalCompanyUen() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "202412345A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("202412345A"))
               .andExpect(jsonPath("$.entityType").value("Local Company"));
    }

    @Test
    void testLocalCompanyUenFail() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "205012345A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("205012345A"))
               .andExpect(jsonPath("$.entityType").value("Invalid"));
    }

    @Test
    void testOtherEntityUen() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "S24LL1234A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("S24LL1234A"))
               .andExpect(jsonPath("$.entityType").value("Other Entity"));
    }
    @Test
    void testOtherEntityUenFailYear() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "T50P61234A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("T50P61234A"))
               .andExpect(jsonPath("$.entityType").value("Invalid"));
    }
    @Test
    void testUenFail() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "L50P61234A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("L50P61234A"))
               .andExpect(jsonPath("$.entityType").value("Invalid"));
    }
    @Test
    void testUenFailOvershot() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "1234567890A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("1234567890A"))
               .andExpect(jsonPath("$.entityType").value("Invalid"));
    }

    @Test
    void testUenFailUndershot() throws Exception{
        mockMvc.perform(get("/uen/validate")
               .param("uen", "12345A"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.uen").value("12345A"))
               .andExpect(jsonPath("$.entityType").value("Invalid"));
    }
}
