package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.ConnectionFactory;

public abstract class AbstractDAO<T, U, V> extends ConnectionFactory<V> implements IGenericDAO<T, U, V> {

	@Override
	public int findNextId(V connName) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Exception ultimaExcecao = null;
		try {
			con = getConnectionCustom(connName);
			statement = this.createStatementFindNextId(con);
			if (statement != null) {
				rs = statement.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}else {
					return 1;
				}

			} else {

				throw new Exception("O Statement est� nulo, n�o � poss�vel efetuar a opera��o.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ultimaExcecao = e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				ultimaExcecao = e;
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		throw ultimaExcecao;
	}
	@Override
	public List<T> list(V connName) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<T> retorno = new ArrayList<T>();
		Exception ultimaExcecao = null;
		try {
			con = getConnectionCustom(connName);
			statement = this.createStatementList(con);
			if (statement != null) {
				rs = statement.executeQuery();
				while (rs.next()) {
					retorno.add(this.parseObject(rs));
				}
				return retorno;
			} else {
				throw new Exception("O Statement est� nulo, n�o � poss�vel efetuar a opera��o.");
			}
		} catch (Exception e) {
			ultimaExcecao = e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}

		throw ultimaExcecao;
	}

	@Override
	public void save(V connName, T ob) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;
		try {
			con = getConnectionCustom(connName);
			statement = this.createStatementSave(con, ob,findNextId(connName));
			if (statement != null) {
				statement.executeUpdate();
				con.commit();
				generatedKeys = statement.getGeneratedKeys();
				generatedKeys.next();
				return;
			} else {
				throw new Exception("O Statement est� nulo, n�o � poss�vel efetuar a opera��o.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ultimaExcecao = e;
			e.printStackTrace();
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}

	@Override
	public T find(V connName, U id) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		T retorno = null;
		Exception ultimaExcecao = null;

		try {
			con = getConnectionCustom(connName);
			statement = this.createStatementFind(con, id);
			if (statement != null) {
				rs = statement.executeQuery();
				if (rs.next())
					retorno = this.parseObject(rs);
				return retorno;
			} else {
				throw new Exception("O Statement est� nulo, n�o � poss�vel efetuar a opera��o.");
			}
		} catch (Exception e) {
			ultimaExcecao = e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}

		throw ultimaExcecao;
	}

	@Override
	public void update(V connName, T ob) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;

		try {
			con = getConnectionCustom(connName);
			statement = this.createStatementUpdate(con, ob);
			statement.executeUpdate();
			con.commit();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			ultimaExcecao = e;
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			if (ultimaExcecao != null)
				throw ultimaExcecao;
	}

	@Override
	public void remove(V connName, U id) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;

		try {
			con = getConnectionCustom(connName);
			statement = this.createStatementRemove(con, id);
			statement.executeUpdate();
			con.commit();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			ultimaExcecao = e;
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {

				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}

	@Override
	public void removeRelacionated(V connName, U id) throws Exception {
		Connection con = null;
		List<PreparedStatement> statements = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;
		T ob = null;
		ob = find(connName, id);
		try {
			con = getConnectionCustom(connName);
			statements = this.createStatementsRemoveRelacionated(con, ob);
			try {
				for (PreparedStatement statement : statements) {
					statement.executeUpdate();
				}
				con.commit();

			} catch (SQLException e) {
				e.printStackTrace();
				ultimaExcecao = e;
				if (con != null)
					con.rollback();
			}

		} catch (Exception e) {
			ultimaExcecao = e;
			e.printStackTrace();
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statements != null)
					for (PreparedStatement statement : statements) {
						try {
							statement.close();
						} catch (SQLException e) {
							ultimaExcecao = e;
						} catch (Exception e) {
							ultimaExcecao = e;
						}
					}

			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}

	@Override
	public void saveRelacionated(V connName, T ob) throws Exception {
		Connection con = null;
		List<PreparedStatement> statements = null;
		ResultSet generatedKeys = null;
		Exception ultimaExcecao = null;
		int n = 0;
		try {
			con = getConnectionCustom(connName);
			statements = this.createStatementsSaveRelacionated(con, ob);

			for (PreparedStatement statement : statements) {
				try {
					n += statement.executeUpdate();
				} catch (SQLException e) {
					ultimaExcecao = e;
				}
			}
			if (n == 5)
				con.commit();
			else {
				con.rollback();
			}
		} catch (Exception e) {
			ultimaExcecao = e;
			e.printStackTrace();
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
			} catch (SQLException e) {
				ultimaExcecao = e;
			}
			try {
				if (statements != null)
					for (PreparedStatement statement : statements) {
						try {
							statement.close();
						} catch (SQLException e) {
							ultimaExcecao = e;
						} catch (Exception e) {
							ultimaExcecao = e;
						}
					}

			} catch (Exception e) {
				ultimaExcecao = e;
			}
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
				ultimaExcecao = e;
			}
		}
		if (ultimaExcecao != null)
			throw ultimaExcecao;
	}
	
	

	protected abstract PreparedStatement createStatementFind(Connection conn, U id) throws Exception;

	protected abstract PreparedStatement createStatementFindNextId(Connection conn) throws Exception;

	protected abstract PreparedStatement createStatementRemove(Connection conn, U id) throws Exception;

	protected abstract PreparedStatement createStatementSave(Connection conn, T ob, int i) throws Exception;

	protected abstract List<PreparedStatement> createStatementsSaveRelacionated(Connection conn, T ob) throws Exception;

	protected abstract List<PreparedStatement> createStatementsRemoveRelacionated(Connection conn, T ob)
			throws Exception;

	protected abstract PreparedStatement createStatementUpdate(Connection conn, T ob) throws Exception;

	protected abstract PreparedStatement createStatementList(Connection conn) throws Exception;

	protected abstract T parseObject(ResultSet rs) throws Exception;
	

}
