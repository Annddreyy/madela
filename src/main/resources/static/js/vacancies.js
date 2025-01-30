let table = document.querySelector('.vacancies-table');

table.addEventListener('click', function(event) {
    let th = event.target.closest('th');
    if (!th) return;

    let sortType = th.textContent;
    
    let index = Array.from(table.tHead.rows[0].cells)
        .map(item => item.textContent)
        .indexOf(sortType);

    let dataType = th.dataset.type;
    
    Array.from(table.tBodies[0].rows)
        .sort((!dataType) 
            ? (a, b) => a.cells[index].textContent.localeCompare(b.cells[index].textContent)
            : (a, b) => a.cells[index].textContent - b.cells[index].textContent)
        .forEach(row => table.tBodies[0].append(row))
});