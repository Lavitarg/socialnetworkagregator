<html>
<head>
    <title>Twitter_test</title>
</head>
<body>
<div>
    Twitter
</div>

<div>
    <#list itemsFromServer as items>
        <h3>Tweet</h3>
        ${items.getTitle()}<br/>
        ${items.getData()}<br/>
        ${items.getText()}<br/>
    </#list>
</div>

</body>
</html>