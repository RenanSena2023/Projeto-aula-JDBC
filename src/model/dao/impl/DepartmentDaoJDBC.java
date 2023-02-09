package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.enties.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null ;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Department "
					+" (Name) "
					+"VALUEs "
					+"(?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1,obj.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected >0) {
				ResultSet rs = st.getGeneratedKeys();
				while(rs.next()) {
					int id= rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("unexpected error ! No rows affecteds");
			}
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE Department "
					+"SET Name = ? WHERE Id = ? ");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			st.execute();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st= conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department WHERE Id = ? ",Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
		
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {

		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));

		return dep;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st= null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"ORDER BY Name");
			
			rs = st.executeQuery();
			List<Department> list = new ArrayList<>();
			
			while(rs.next()) {
				list.add(new Department(rs.getInt("Id"),rs.getString("Name")));
			}
			
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
		
	}

}
