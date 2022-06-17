package databaseProcesses;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISqlHelper {

	// Insert
	void insertData(String inserexecute) throws SQLException;

	// Update
	void updateData(String updateexecute) throws SQLException;

	// Delete
	void deleteData(String deleteExecute) throws SQLException;

	// Select
	ResultSet selectData(String SelectDataExecute) throws SQLException;

}
