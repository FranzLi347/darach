// 请求的数据
const requestData = {
    "current": 0,
    "pageSize": 20,
    "sortField": "id",
    "sortOrder": "ascend"
};

// 发送请求
fetch('http://localhost:8080/animate/list', {
    method: 'POST', // 或者 'GET'
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(requestData),
})
.then(response => response.json())
.then(data => {
    // 获取表格的tbody元素
    const tbody = document.getElementById('animateTable').getElementsByTagName('tbody')[0];

    // 对于每条记录，创建一个新的表格行并插入到tbody中
    data.records.forEach(record => {
        const row = document.createElement('tr');

        Object.values(record).forEach(value => {
            const cell = document.createElement('td');
            cell.textContent = value;
            row.appendChild(cell);
        });

        tbody.appendChild(row);
    });
})
.catch((error) => {
    console.error('Error:', error);
});
