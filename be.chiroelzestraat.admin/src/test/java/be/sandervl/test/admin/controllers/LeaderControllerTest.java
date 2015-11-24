package be.sandervl.test.admin.controllers;

import be.sandervl.admin.ChiroAdminModule;
import com.foreach.across.core.AcrossContext;
import com.foreach.across.database.support.HikariDataSourceHelper;
import com.foreach.across.modules.entity.EntityModule;
import com.foreach.across.modules.hibernate.AcrossHibernateModule;
import com.foreach.across.modules.web.AcrossWebModule;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringJUnit4ClassRunner.class)
//@DirtiesContext
//@WebAppConfiguration
//@ContextConfiguration(classes = LeaderControllerTest.Config.class)
public class LeaderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void initMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(get("/secure/entities/leader/create"))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                    @Override
                    public void handle(MvcResult mvcResult) throws Exception {
                        String content = mvcResult.getResponse().getContentAsString();
                        assertTrue(StringUtils.contains(content, "create leader"));
                    }
                });
    }

//    @Configuration
    static class Config {
        @Bean
        public DataSource dataSource() throws Exception {
            return HikariDataSourceHelper.create("org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:mem:acrosscore", "sa",
                    StringUtils.EMPTY);
        }

        @Bean
        public AcrossContext acrossContext(ConfigurableApplicationContext applicationContext) throws Exception {
            AcrossContext context = new AcrossContext(applicationContext);
            context.setDataSource(dataSource());
            context.addModule(acrossHibernateModule());
            context.addModule(new ChiroAdminModule());
            context.addModule(new AcrossWebModule());
            context.addModule(new EntityModule());
            context.bootstrap();
            return context;
        }

        public AcrossHibernateModule acrossHibernateModule() {
            AcrossHibernateModule module = new AcrossHibernateModule();
            module.setHibernateProperty("hibernate.hbm2ddl.auto", "create-drop");

            return module;
        }
    }
}