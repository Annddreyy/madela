let surveys = [];
let optionCount = 1;

function addOption() {
    optionCount++;
    const optionsContainer = document.getElementById('optionsContainer');
    const newOption = document.createElement('div');
    newOption.innerHTML = `
        <label for="option${optionCount}">Вариант ${optionCount}:</label>
        <input type="text" id="option${optionCount}" name="option${optionCount}" required>
        <button type="button" onclick="removeOption(${optionCount})">Удалить вариант</button>
    `;
    optionsContainer.appendChild(newOption);
}

function removeOption(optionNumber) {
    const optionElement = document.getElementById(`option${optionNumber}`).parentElement;
    optionElement.remove();
}

function addSurvey() {
    const surveyName = document.getElementById('surveyName').value;
    const options = [];
    for (let i = 1; i <= optionCount; i++) {
        const optionElement = document.getElementById(`option${i}`);
        if (optionElement) {
            options.push(optionElement.value);
        }
    }
    if (surveyName && options.length > 0) {
        surveys.push({ name: surveyName, options: options });
        renderSurveys();
        document.getElementById('surveyForm').reset();
        optionCount = 1;
        document.getElementById('optionsContainer').innerHTML = `
            <label for="option1">Вариант 1:</label>
            <input type="text" id="option1" name="option1" required>
        `;
    }
}

function renderSurveys() {
    const surveyList = document.getElementById('surveyList');
    surveyList.innerHTML = '';
    surveys.forEach((survey, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${survey.name}</td>
            <td>${survey.options.join(', ')}</td>
            <td>
                <button onclick="editSurvey(${index})">Редактировать</button>
                <button class="delete" onclick="deleteSurvey(${index})">Удалить</button>
            </td>
        `;
        surveyList.appendChild(tr);
    });
}

function editSurvey(index) {
    const survey = surveys[index];
    document.getElementById('surveyName').value = survey.name;
    document.getElementById('optionsContainer').innerHTML = '';
    survey.options.forEach((option, idx) => {
        const newOption = document.createElement('div');
        newOption.innerHTML = `
            <label for="option${idx + 1}">Вариант ${idx + 1}:</label>
            <input type="text" id="option${idx + 1}" name="option${idx + 1}" value="${option}" required>
            <button type="button" onclick="removeOption(${idx + 1})">Удалить вариант</button>
        `;
        document.getElementById('optionsContainer').appendChild(newOption);
    });
    optionCount = survey.options.length;
    surveys.splice(index, 1);
}

function deleteSurvey(index) {
    surveys.splice(index, 1);
    renderSurveys();
}

// Initial render
renderSurveys();
