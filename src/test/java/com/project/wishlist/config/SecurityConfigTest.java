package com.project.wishlist.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void contextLoads() {
        assert context != null;
    }

    @Test
    public void userDetailsServiceLoads() {
        assert userDetailsService != null;
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testHttpBasicAuthentication() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();

        mockMvc.perform(get("/api/wishlist/{clientId}/product/{productId}", "clientID01XPTO", "1")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testHttpBasicAuthenticationFails() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();

        mockMvc.perform(get("/api/wishlist/someClientId")
                        .with(httpBasic("invalid", "invalid")))
                .andExpect(status().isUnauthorized());
    }
}