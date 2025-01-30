let faqList = document.getElementById('faq-list');
faqList.addEventListener('click', function(event) {
    let target = event.target.closest('.delete-button, .update-button');
    if (!target) return;
    if (target.classList.contains('delete-button')) {
        target.parentElement.remove();
    } else {
        let form = document.createElement('form');
        form.classList.add('faq-form');
        form.innerHTML = `
            <h2>Редактировать FAQ</h2>
            <label for="question">Вопрос</label>
            <input type="text" name="question" id="question" >
            <label for="answer">Ответ</label>
            <input type="text" name="answer" id="answer">
            <input type="submit" value="Редактировать">
        `
        let faq = target.parentElement;
        if (faq.nextElementSibling?.tagName == 'FORM') return;
        faq.after(form);
    }
});