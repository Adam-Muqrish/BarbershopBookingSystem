<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, com.hugi.barbershop.common.util.DBUtil" %>
<!DOCTYPE html>
<html>
<head>
    <title>DB Connection Test</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 2rem; }
        .success { color: green; }
        .fail { color: red; }
        pre { background-color: #f0f0f0; padding: 1rem; border-radius: 5px; }
    </style>
</head>
<body>
    <h1>Database Connection Test</h1>
    <%
        Connection conn = null;
        try {
            out.println("<p>Trying to connect...</p>");
            conn = DBUtil.getConnection();

            if (conn != null && !conn.isClosed()) {
                out.println("<p class='success'>✅ Connection successful!</p>");
                out.println("<p>Auto commit: " + conn.getAutoCommit() + "</p>");
            } else {
                out.println("<p class='fail'>❌ Connection object is null or closed.</p>");
            }
        } catch (SQLException e) {
            out.println("<p class='fail'>❌ SQL Exception occurred:</p>");
            out.println("<pre>" + e.toString() + "</pre>");
        } catch (Exception e) {
            out.println("<p class='fail'>❌ Unexpected error occurred:</p>");
            out.println("<pre>" + e.toString() + "</pre>");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    out.println("<p>Connection closed.</p>");
                } catch (SQLException e) {
                    out.println("<p class='fail'>⚠️ Failed to close connection:</p>");
                    out.println("<pre>" + e.toString() + "</pre>");
                }
            }
        }
    %>
</body>
</html>
