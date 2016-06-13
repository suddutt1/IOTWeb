<%@ tag body-content="empty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${actionError != null }" >
	<div class="alert alert-danger">
    	<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    	<strong>Login error</strong> Please provide correct credentials
 	</div>
</c:if>
