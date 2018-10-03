<%-- 
    Document   : error
    Created on : Oct 2, 2018, 2:44:28 PM
    Author     : Frank
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
   
   <c:forEach var="err" items="${dataerror}">
                                 
                         <tr>
                    
                    
                    <c:if test="${err.equals(database_erroe)}">
                       
                        <% myFunction(); %>
                      
                       </c:if>
                    </td>   
                    
                </tr>
            </c:forEach>
                
                <script>
function myFunction() {
    alert("Database error");
}
</script>
 
 </body>
</html>
