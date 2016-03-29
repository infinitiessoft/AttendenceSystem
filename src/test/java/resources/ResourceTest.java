package resources;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.dbunit.DatabaseUnitException;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.RequestContextFilter;

@ContextConfiguration("file:src/test/resource/test_context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = { ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class })
public abstract class ResourceTest extends JerseyTest {

	@Autowired
	protected DbUnitUtil dbUtil;

	private static boolean isLoad = false;

	@Override
	protected TestContainerFactory getTestContainerFactory() {
		return new GrizzlyWebTestContainerFactory();
	}

	@Override
	protected DeploymentContext configureDeployment() {
		return ServletDeploymentContext
				.forServlet(new ServletContainer(configure()))
				.addListener(ContextLoaderListener.class)
				.contextParam("contextConfigLocation",
						"file:src/test/resource/test_context.xml")
				.addFilter(
						org.springframework.web.filter.DelegatingFilterProxy.class,
						"springSecurityFilterChain").build();
	}

	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		ResourceConfig rc = new ResourceConfig(getResource());
		rc.register(GenericExceptionMapper.class);
		rc.register(SpringLifecycleListener.class);
		rc.register(RequestContextFilter.class);
		rc.register(JacksonFeature.class);
		// rc.register(AuthenticationFilter.class);
		rc.property("contextConfigLocation",
				"file:src/test/resource/test_context.xml");
		return rc;
	}

	protected abstract Class<?>[] getResource();

	@Before
	public void initData() throws HibernateException, DatabaseUnitException,
			SQLException {

		// // As your project and list of tables grows, specifying
		// // what tables to load will be more important
		// dbUtil.createTables(new Class[] { Employee.class });

		// Different functional tests require different data sets
		if (!isLoad) {
			isLoad = true;
			dbUtil.loadData();
		}
	}

	@AfterClass
	public static void end() {
		isLoad = false;
	}

	@Provider
	@Priority(1000)
	public static class AuthenticationFilter implements ContainerRequestFilter {

		@Autowired
		private UserDetailsService userDetailsService;

		@Override
		public void filter(ContainerRequestContext requestContext)
				throws IOException {
			System.err.println("...................inject user:"
					+ requestContext.getHeaderString("user"));
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(requestContext.getHeaderString("user"));
			Authentication authToken = new UsernamePasswordAuthenticationToken(
					userDetails, userDetails, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);

			requestContext.setSecurityContext(new SecurityContext() {
				@Override
				public boolean isUserInRole(String role) {
					return userDetails.getAuthorities().contains(role);
				}

				@Override
				public boolean isSecure() {
					return false;
				}

				@Override
				public Principal getUserPrincipal() {
					return new Principal() {

						@Override
						public String getName() {
							return userDetails.getUsername();
						}
					};
				}

				@Override
				public String getAuthenticationScheme() {
					return null;
				}
			});
		}
	}

}
