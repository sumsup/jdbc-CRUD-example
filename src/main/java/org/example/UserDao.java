package org.example;

import java.sql.*;

public class UserDao {

//    public void create(User user) throws SQLException {
//        Connection con = null;
//        java.sql.PreparedStatement pstmt = null;
//
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, user.getUserId());
//            pstmt.setString(2, user.getPassword());
//            pstmt.setString(3, user.getName());
//            pstmt.setString(4, user.getEmail());
//
//            pstmt.executeUpdate();
//        } finally {
//            if (pstmt != null) {
//                pstmt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
//    }

    public void create(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
//        jdbcTemplate.executeQuery(sql, new PreparedStatementSetter() {
//                    @Override
//                    public void setter(PreparedStatement pstmt) throws SQLException {
//                        pstmt.setString(1, user.getUserId());
//                        pstmt.setString(2, user.getPassword());
//                        pstmt.setString(3, user.getName());
//                        pstmt.setString(4, user.getEmail());
//                    }
//        });

        // 위 주석처리된 코드를 람다로 바꿈. 람다 => 함수형.
        // 함수형 : abstract method가 하나뿐인 인터페이스.
        // 파라미터가 하나면 람다로 변경 할 수 있다.
        jdbcTemplate.executeQuery(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

//    public User findByUserId(String userId) throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet resultSet = null;
//
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, userId);
//
//            resultSet = pstmt.executeQuery();
//
//            User user = null;
//            if (resultSet.next()) {
//                user = new User(
//                        resultSet.getString("userId"),
//                        resultSet.getString("password"),
//                        resultSet.getString("name"),
//                        resultSet.getString("email"));
//            }
//
//            return user;
//
//        } finally {
//            if (resultSet != null) {
//                resultSet.close();
//            }
//
//            if (pstmt != null) {
//                pstmt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
//    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";

        return (User) jdbcTemplate.executeQuery(sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        }, new RowMapper() {
            @Override
            public Object map(ResultSet resultSet) throws SQLException {
                return new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email"));
            }

        });
    }
}
