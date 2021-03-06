package oline_fresh_supermaket.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import java.sql.Date;

import oline_fresh_supermaket.ift.IuserManager;
import oline_fresh_supermaket.model.Beanuser;
import oline_fresh_supermaket.util.BaseException;
import oline_fresh_supermaket.util.BusinessException;
import oline_fresh_supermaket.util.DbException;
import oline_fresh_supermaket.util.JDBCUtil;

public class userManager implements IuserManager {
	@Override
	public Beanuser login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		Beanuser result =new Beanuser();
		if(userid.isEmpty()) throw new BusinessException("用户名不能为空！");
		if(pwd.isEmpty()) throw new BusinessException("密码不能为空！");
		Connection conn = null;
		try {
			conn=JDBCUtil.getConnection();
			String sql = "select * from user where usr_name = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) 
				throw new BusinessException("用户名不存在,登陆失败");
			result.setUsr_id(rs.getInt(1));
			result.setUsr_name(rs.getString(2));
			result.setUsr_gender(rs.getNString(3));
			result.setUsr_pwd(rs.getString(4));
			result.setUsr_phonenumber(rs.getString(5));
			result.setUsr_email(rs.getString(6));
			result.setUsr_city(rs.getString(7));
			result.setUsr_registration_time(rs.getDate(8));
			result.setUsr_isvip(rs.getBoolean(9));
			result.setUsr_vip_ddl(rs.getDate(10));
			if(!result.getUsr_pwd().equals(pwd)) {
				throw new BusinessException("密码错误。");
			}
			pst.close();
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	@Override
	public Beanuser reg(String name, String gender, char[] pwd,char[] pwd2,String telephone, String city, String email)throws BaseException
	{
		// TODO Auto-generated method stub
		Beanuser result =  new Beanuser();
		if(name.isEmpty()) throw new BaseException("用户名不能为空！");
		if(!String.valueOf(pwd).equals(String.valueOf(pwd2))) throw new BaseException("两次输入密码不相同！");
		if(String.valueOf(pwd).isEmpty()) throw new BaseException("密码不能为空！");
		if (gender.isEmpty())	throw new BaseException("请选择性别选项！");
		if(email.isEmpty())	throw new BaseException("电子邮箱不能为空");
		if(city.isEmpty())	throw new BaseException("所在城市不能为空");
		if(telephone.isEmpty()) throw new BaseException("电话不能为空！");
		Connection conn = null;
		try {
			conn=JDBCUtil.getConnection();
			String sql = "select max(usr_id) from user";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) result.setUsr_id(1);
			result.setUsr_id(rs.getInt(1)+1);
			rs.close();
			
			//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//Date date = new Date(); 
			result.setUsr_name(name);
			result.setUsr_gender(gender);
			result.setUsr_pwd(String.valueOf(pwd));
			result.setUsr_phonenumber(telephone);
			result.setUsr_city(city);
			result.setUsr_email(email);
			result.setUsr_isvip(false);
			result.setUsr_registration_time(new java.sql.Date(System.currentTimeMillis()));
			
			sql = "insert into user(usr_id,usr_name,usr_gender,"
					+ "usr_pwd,usr_phonenumber,usr_city,usr_email,usr_registration_time,usr_isvip) values (?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, result.getUsr_id());
			pst.setString(2, name);
			pst.setString(3, gender);
			pst.setString(4, String.valueOf(pwd));
			pst.setString(5, telephone);
			pst.setString(6, city);
			pst.setString(7, email);
			pst.setDate(8,new java.sql.Date(System.currentTimeMillis()));
			pst.setBoolean(9, false);
			pst.execute();
			pst.close();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	@Override
	public boolean isvip(String name) throws BaseException {
		// TODO Auto-generated method stub
		boolean result ;
		Connection conn = null;
		try {
			conn=JDBCUtil.getConnection();
			String sql = "select usr_isvip from user where usr_name = ? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, name);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BaseException("该用户不存在");
			result = rs.getBoolean(1);
			
			pst.close();
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	@Override
	public void bevip(String name) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		boolean p = isvip(name);
		if(p) JOptionPane.showMessageDialog(null,"已经是VIP会员。可到期后续费！");
		try {
			conn=JDBCUtil.getConnection();
			String sql = "update user set usr_isvip = true where usr_name = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.execute();
			pst.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public Date setDDLDate(String name,Date addDate) throws BaseException {
		// TODO Auto-generated method stub
		java.sql.Date sqlDate;
		Connection conn = null;
		try {
			conn=JDBCUtil.getConnection();
			String sql = "update user set usr_vip_ddl = ? where usr_name = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			
			java.util.Date utilDate = new java.util.Date();
			sqlDate = new java.sql.Date(utilDate.getTime()+addDate.getTime());
			pst.setDate(1, sqlDate);
			pst.setString(2, name);
			pst.execute();
			pst.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return sqlDate;
	}

	@Override
	public Date searchDDLDate(String userid) throws BaseException {
		// TODO Auto-generated method stub
		java.sql.Date sqlDate;
		Connection conn = null;
		try {
			conn=JDBCUtil.getConnection();
			String sql = "select usr_vip_ddl from user where usr_name = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) JOptionPane.showMessageDialog(null,"未开通vip。");
			sqlDate = rs.getDate(1);
			rs.close();
			pst.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return sqlDate;
	}

	@Override
	public Beanuser update(String usr_name, String gender, String phonenumber, String emailString, String cityString)
			throws BaseException {
		// TODO Auto-generated method stub
		Beanuser result = new Beanuser();
		Connection conn = null;
		if(usr_name.isEmpty()||gender.isEmpty()||phonenumber.isEmpty()||emailString.isEmpty()||cityString.isEmpty()) {
			JOptionPane.showMessageDialog(null,"输入的参数不能为空！");
		}
		try {
			conn=JDBCUtil.getConnection();
			String sql = "update user set usr_name = ?,usr_gender = ?,usr_phonenumber = ?,usr_email = ?,usr_city = ? where usr_id =?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, usr_name);
			pst.setString(2, gender);
			pst.setString(3, phonenumber);
			pst.setString(4, emailString);
			pst.setString(5, cityString);
			pst.setInt(6, Beanuser.currentLoginUser.getUsr_id());
			pst.execute();
			
			pst.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	@Override
	public Beanuser searchUser(int usr_id) throws BaseException {
		// TODO Auto-generated method stub
		Beanuser result = new Beanuser();
		Connection conn = null;
		try {
			conn=JDBCUtil.getConnection();
			String sql = "select * from user where usr_id =?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, usr_id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) 
				throw new BusinessException("用户名不存在");
			result.setUsr_id(rs.getInt(1));
			result.setUsr_name(rs.getString(2));
			result.setUsr_gender(rs.getNString(3));
			result.setUsr_pwd(rs.getString(4));
			result.setUsr_phonenumber(rs.getString(5));
			result.setUsr_email(rs.getString(6));
			result.setUsr_city(rs.getString(7));
			result.setUsr_registration_time(rs.getDate(8));
			result.setUsr_isvip(rs.getBoolean(9));
			result.setUsr_vip_ddl(rs.getDate(10));
			
			pst.close();
			rs.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

}
