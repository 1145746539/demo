package com.yc.dingcan.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.jdbc.Statement;
/**
 * 数据库操作助手类，实现上下文监听器接口，可加载数据库参数
 * @author 廖彦
 *
 */
public class DBHelper implements ServletContextListener {
	public static String URL = "jdbc:mysql://lenovo/dingcan?useUnicode=true&amp;characterEncoding=UTF-8";
	public static String USR = "root";
	public static String PWD = "a";
	public static String DRV = "com.mysql.jdbc.Driver";

	static {
		init();
	}

	private static void init() {
		try {
			Class.forName(DRV);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getCon() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USR, PWD);
//			System.out.println("链接成功");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return con;
	}

	public static void close(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param sql           
	 * @param params		
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static Integer doInsert(String sql, Object... params) {
		
		Connection con = getCon();
		PreparedStatement pstm = null;
		
		try {
			System.out.println("SQL:" + sql);
			pstm = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			doParams(pstm, params);
			//获取自增列值结果集
			pstm.executeUpdate();
			ResultSet rs=pstm.getGeneratedKeys();
			Integer key=null;
			if(rs.next()){
				//获取自增列值，先转字符串，再转Integer
				key=Integer.parseInt(rs.getString(1));
				System.out.println("insert row and return generated key "+key);
			}
			return key;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(con);
		}
	}
	
	public static int doUpdate(String sql, Object...params){
		
		Connection con = getCon();
		PreparedStatement pstm = null;
		
		try {
			System.out.println("SQL:" + sql);
			pstm = con.prepareStatement(sql);
			doParams(pstm, params);
			int rows = pstm.executeUpdate();
			System.out.println("update rows " + rows);
			return rows;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(con);
		}
		
	}

	@SuppressWarnings("unchecked")
	public static void doParams(PreparedStatement pstm, Object... params) {
		try {
			int i = 1;
			for (Object o : params) {
				if (o instanceof Collection) {
					for (Object p : (Collection<Object>) o) {
						pstm.setObject(i++, p);
					}
				} else {
					pstm.setObject(i++, o);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return   鍥犱负鏌ヨ鍑烘潵鐨勬槸涓�涓敭鍊煎褰㈠紡鐨勶紝鍒楁槑涓洪敭锛屽�间负鍊硷紝鎵�浠ユ垜浠�
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> findAll(String sql, Object... params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = getCon();
		PreparedStatement pstm = null;
		//鏌ヨ寰楀埌缁撴灉闆�
		ResultSet rs = null;
		try {
			System.out.println("SQL:" + sql);
			pstm = con.prepareStatement(sql);
			doParams(pstm, params);
			rs = pstm.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] columnName = new String[rsmd.getColumnCount()];
			for (int i = 0; i < columnName.length; i++) {
				columnName[i] = rsmd.getColumnName(i + 1);
			}

			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String cn : columnName) {
					map.put(cn, rs.getObject(cn));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			close(con);
		}

		return list;
	}

	public static <T> List<T> find(String sql, Class<T> c, Object... params) {
		List<T> list = new ArrayList<T>(); //瑕佽繑鍥炵殑缁撴灉鐨勯泦鍚�
		Connection con = getCon(); //鑾峰彇杩炴帴
		ResultSet rs;
		PreparedStatement pstmt;

		try {
			System.out.println("SQL:" + sql);
			pstmt = con.prepareStatement(sql); //棰勭紪璇戝璞�
			doParams(pstmt, params); //璁剧疆鍗犱綅绗�
			rs = pstmt.executeQuery(); //鎵ц鏌ヨ璇彞锛屽緱鍒扮粨鏋滈泦

			Method[] ms = c.getMethods(); //鍙栧嚭杩欎釜鍙嶅皠瀹炰緥鐨勬墍鏈夋柟娉�
			ResultSetMetaData md = rs.getMetaData(); //鑾峰彇缁撴灉闆嗙殑鍏冩暟鎹紝瀹冨弽鏄犱簡缁撴灉闆嗙殑淇℃伅

			String[] colnames = new String[md.getColumnCount()];//鍒涘缓涓�涓暟鎹甤olnames锛岀敤鏉ュ瓨鏀惧垪鐨勫悕瀛�
			for (int i = 0; i < colnames.length; i++) { //灏嗗垪鍚嶄繚瀛樺埌colname鏁扮粍涓�
				colnames[i] = md.getColumnName(i + 1);
			}

			T t;
			String mname = null; //鏂规硶鍚�
			String cname = null; //鍒楀悕

			while (rs.next()) {
				t = (T) c.newInstance(); //鍒涘缓鍙嶅皠绫荤殑瀹炰緥鍖栧璞�    Product t=(Product)c.newInstance();
				for (int i = 0; i < colnames.length; i++) {//寰幆鏂规硶鍚� ,鏍煎紡涓簊etXXXX鎴杇etXXX
					//空值忽略
					Object value = rs.getObject(colnames[i]);
					if (value == null) {
						continue;
					}
					cname = colnames[i]; //鍙栧嚭鍒楀悕骞跺湪鍓嶉潰鍔犱笂set  setXXX
					cname = "set" + cname.substring(0, 1).toUpperCase() + cname.substring(1).toLowerCase();
					if (ms != null && ms.length > 0) {
						for (Method m : ms) {//寰幆鍒楀悕
							mname = m.getName(); //鍙栧嚭鏂规硶鍚�
							if (cname.equals(mname)) {//鍒ゆ柇鏂规硶鍚嶅拰鍒楀悕鏄惁涓�鏍凤紝鐩稿悓鍒欐縺娲绘柟娉曪紝娉ㄥ叆鏁版嵁                           //鍙"set"+鏁版嵁鍒楀悕.equalsIgnoreCase锛堟柟娉曞悕锛夛紝鍒欐縺娲昏繖涓柟娉�
								//获取方法参数的类型
								Class<?> cls = m.getParameterTypes()[0];
								//转小写
								String clsName = cls.getSimpleName().toLowerCase();
								switch (clsName) {
								case "byte":
									m.invoke(t, rs.getByte(colnames[i]));
									break;
								case "short":
									m.invoke(t, rs.getShort(colnames[i]));
									break;
								case "int":
								case "integer":
									m.invoke(t, rs.getInt(colnames[i]));
									break;
								case "long":
									m.invoke(t, rs.getLong(colnames[i]));
									break;
								case "float":
									m.invoke(t, rs.getFloat(colnames[i]));
									break;
								case "double":
									m.invoke(t, rs.getDouble(colnames[i]));
									break;
								case "string":
									m.invoke(t, rs.getString(colnames[i]));
									break;
								case "boolean":
									m.invoke(t, rs.getBoolean(colnames[i]));
									break;
								case "date":
									m.invoke(t, rs.getDate(colnames[i]));
									break;
								case "timestamp":
									m.invoke(t, rs.getTimestamp(colnames[i]));
									break;
								case "byte[]":
									BufferedInputStream is = null;
									byte[] bytes = null;
									Blob blob = rs.getBlob(colnames[i]);
									try {
										is = new BufferedInputStream(blob.getBinaryStream());
										bytes = new byte[(int) blob.length()];
										is.read(bytes);
									} catch (Exception e) {
										e.printStackTrace();
									}
									m.invoke(t, bytes);
									break;
								default:
									System.out.println("未知类型：" + clsName + "===>" + value + "，听天由命了！");
									m.invoke(t, value);
								}
								break;
							}
						}
					}
				}
				list.add(t);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(con);
		}
		System.out.println("select rows " + list.size());
		return list;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//从上下文初始化参数中加载连接参数
		String driver = sce.getServletContext().getInitParameter("dbDriver");
		if (driver != null) {
			DRV = driver;
			URL = sce.getServletContext().getInitParameter("dbUrl");
			USR = sce.getServletContext().getInitParameter("dbUser");
			PWD = sce.getServletContext().getInitParameter("dbPwd");
		}
	}

}
