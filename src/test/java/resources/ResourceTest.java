package resources;

import java.sql.SQLException;

import javax.ws.rs.core.Application;

import org.dbunit.DatabaseUnitException;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.filter.RequestContextFilter;

@ContextConfiguration("file:src/test/resource/test_context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class ResourceTest extends JerseyTest {

	@Autowired
	protected DbUnitUtil dbUtil;

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		ResourceConfig rc = new ResourceConfig(getResource());
		rc.register(GenericExceptionMapper.class);
		rc.register(SpringLifecycleListener.class);
		rc.register(RequestContextFilter.class);
		rc.register(JacksonFeature.class);

		rc.property("contextConfigLocation",
				"file:src/test/resource/test_context.xml");
		return rc;
	}

	abstract Class<?> getResource();

	@Before
	public void initData() throws HibernateException, DatabaseUnitException,
			SQLException {

		// // As your project and list of tables grows, specifying
		// // what tables to load will be more important
		// dbUtil.createTables(new Class[] { Employee.class });

		// Different functional tests require different data sets
		dbUtil.loadData();
	}

}
