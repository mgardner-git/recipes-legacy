<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"/>
<script type="text/javascript" src="resources/js/directives/selector.js"></script>
<script type="text/javascript" src="resources/js/directives/ng-size.js"></script>
<script type="text/javascript" src="resources/js/directives/numeric.js"></script>
<script type="text/javascript" src="resources/js/ingredients/analyzeIngredients.js"></script>

<style>
	#container ul
	{
		list-style-type: none;
	}
	#selectedIngredients li, #recipes li, #connectedIngredients li
	{
		position: absolute;
		width: 150px;
		hieght: 50px;
		border: 1px solid black;
		
	}
	#connectedIngredients li.inSelected
	{
		border: 2px solid green;
	}
	#connectedIngredients li.notInSelected
	{
		border: 2px dotted red;
	}
	
	#container
	{
		width: 100%;
		position: relative;
	}
</style>

<div ng-controller="analyzeController">
	<selector items="myIngredients" selected-items="selectedIngredients"></selector>
	<div>
		<label for="threshold">Threshold:</label>
		<numeric field-name="threshold"></numeric>
	  	<input type="number" name="threshold" ng-model="threshold" min="0" max="99">
	</div>
	<div id="container" ng-class="notInSelected" >
		<ul id="selectedIngredients">
			<li ng-repeat = "ingredient in selectedIngredients">
				{{ingredient.label}}
			</li>
		</ul>
		<ul id="recipes">
			<li ng-repeat = "recipe in recipes" ng-attr-id="{{ 'recipe-' + recipe.id }}">
				{{recipe.title}}
			</li>
		</ul>
		<ul id="connectedIngredients">
			<li  ng-class="getClass(ingredient)" ng-repeat="ingredient in connectedIngredients" ng-attr-id="{{'ingredient-' + ingredient.id}}">			
				{{ingredient.title}}
			</li>
		</ul>
	</div>
</div>
<jsp:include page="footer.jsp"/>