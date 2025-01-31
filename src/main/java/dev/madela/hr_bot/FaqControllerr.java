package dev.madela.hr_bot;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faqs")
@Validated
public class FaqControllerr {

    private final List<Faq> faqList = new ArrayList<>();


    @GetMapping
    public ResponseEntity<List<Faq>> getAllFaqs() {
        return ResponseEntity.ok(faqList);
    }


    @PostMapping
    public ResponseEntity<Faq> addFaq(@RequestBody @Validated Faq faq) {
        faq.setId(faqList.size() + 1);
        faqList.add(faq);
        return ResponseEntity.ok(faq);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Faq> updateFaq(@PathVariable int id, @RequestBody @Validated Faq updatedFaq) {
        Optional<Faq> existingFaq = faqList.stream().filter(faq -> faq.getId() == id).findFirst();

        if (existingFaq.isPresent()) {
            Faq faq = existingFaq.get();
            faq.setQuestion(updatedFaq.getQuestion());
            return ResponseEntity.ok(faq);
        } else {
            throw new EntityNotFoundException("FAQ с ID " + id + " не найдено");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable int id) {
        boolean removed = faqList.removeIf(faq -> faq.getId() == id);

        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("FAQ с ID " + id + " не найдено");
        }
    }


    public static class Faq {
        private int id;

        @NotBlank(message = "Вопрос не может быть пустым.")
        @Size(max = 255, message = "Вопрос не может превышать 255 символов.")
        private String question;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }
}

/*
curl -X GET http://localhost:8080/faqs
curl -X POST http://localhost:8080/faqs -H "Content-Type: application/json" -d "{\"question\": \"Тест занесения\"}"
curl -X PUT http://localhost:8080/faqs/1 -H "Content-Type: application/json" -d "{\"question\": \"Тест обновления\"}"
curl -X DELETE http://localhost:8080/faqs/1
*/

