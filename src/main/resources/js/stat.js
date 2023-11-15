google.charts.load("current", {packages: ['corechart']});
google.charts.setOnLoadCallback(drawOrderStatus);
google.charts.setOnLoadCallback(drawOrderPrice);
google.charts.setOnLoadCallback(drawProductStatus);

function drawOrderStatus() {
    var status1 = parseInt(document.querySelector('#orderingStatus1').textContent);
    var status2 = parseInt(document.querySelector('#orderingStatus2').textContent);
    var status3 = parseInt(document.querySelector('#orderingStatus3').textContent);
    var status4 = parseInt(document.querySelector('#orderingStatus4').textContent);
    var status5 = parseInt(document.querySelector('#orderingStatus5').textContent);
    var data = google.visualization.arrayToDataTable([
        ['Статус', 'Заказы', {role: 'style'}, {role: 'annotation'}],
        ['Ожидание', status1, "red", status1],
        ['Зарезервировано', status2, "brown", status2],
        ['Не зарезервировано', status3, "green", status3],
        ['В отгрузке', status4, "blue", status4],
        ['Отгружено', status5, "purple", status5],
    ]);


    var options = {
        title: 'Статусы заказов',
        hAxis: {title: 'Статус'},
        vAxis: {title: 'Количество'},
        bar: {groupWidth: "80%"},
        legend: {position: "none"}
    };
    var chart = new google.visualization.ColumnChart(document.getElementById('orderingStatus'));
    chart.draw(data, options);
}

function drawOrderPrice() {
    var price1 = parseInt(document.querySelector('#orderingPrice1').textContent);
    var price2 = parseInt(document.querySelector('#orderingPrice2').textContent);
    var price3 = parseInt(document.querySelector('#orderingPrice3').textContent);
    var price4 = parseInt(document.querySelector('#orderingPrice4').textContent);
    var price5 = parseInt(document.querySelector('#orderingPrice5').textContent);
    var data = google.visualization.arrayToDataTable([
        ['Статус', 'Цена', {role: 'style'}, {role: 'annotation'}],
        ['Ожидание', price1, "red", price1],
        ['Зарезервировано', price2, "brown", price2],
        ['Не зарезервировано', price3, "green", price3],
        ['В отгрузке', price4, "blue", price4],
        ['Отгружено', price5, "purple", price5],
    ]);
    var options = {
        title: 'Цена заказов',
        hAxis: {title: 'Статус'},
        vAxis: {title: 'Количество'},
        bar: {groupWidth: "80%"},
        legend: {position: "none"}
    };
    var chart = new google.visualization.ColumnChart(document.getElementById('orderingPrice'));
    chart.draw(data, options);
}

function drawProductStatus() {
    var product1 = parseInt(document.querySelector('#productStatus1').textContent);
    var product2 = parseInt(document.querySelector('#productStatus2').textContent);
    var product3 = parseInt(document.querySelector('#productStatus3').textContent);
    var data = google.visualization.arrayToDataTable([
        ['Состояние', 'Количество'],
        ['Произведено', product1],
        ['Зарезервировано', product2],
        ['Отгружено', product3]
    ]);

    var options = {
        title: 'Состояние товаров',
        pieHole: 0.4,
    };

    var chart = new google.visualization.PieChart(document.getElementById('productStatus'));
    chart.draw(data, options);
}