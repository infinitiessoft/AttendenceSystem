package resources;

import java.io.File;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.HibernateException;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dbUtil")
public class DbUnitUtil {

	private IDatabaseConnection connection;

	private EntityManager entityManager;

	@Transactional
	public void loadData() throws HibernateException, DatabaseUnitException,
			SQLException {
		connection = new DatabaseConnection(
				((SessionImpl) (entityManager.getDelegate())).connection());
		connection.getConfig().setProperty(
				DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new HsqldbDataTypeFactory());

		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);
		// InputStream dataSet =
		// Thread.currentThread().getContextClassLoader().getResourceAsStream("test-data.xml");
		// dataset = flatXmlDataSetBuilder.build(dataSet);
		// DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

		IDataSet dataSet = new CsvDataSet(new File(
				"src/test/resource/test-dataset"));
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
