document.addEventListener("DOMContentLoaded", function() {
  
    const formsAjax = document.querySelectorAll('.form-ajax');

    formsAjax.forEach(form => {
        //se il form ha già l'attributo 'data-ajax-ready', non aggiungiamo un altro listener
        if (form.getAttribute('data-ajax-ready') === 'true') return;

        form.addEventListener('submit', function(event) {
            event.preventDefault();

            const url = this.getAttribute('action');
            const formData = new URLSearchParams(new FormData(this));

            fetch(url, {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                const badge = document.getElementById('badge-carrello');
                if (badge) {
                    badge.textContent = data.totaleElementi;

               badge.classList.remove('badge-nascosto');

               badge.style.transform = 'scale(1.3)';
               setTimeout(() => badge.style.transform = 'scale(1)', 200);
              }
            })
            .catch(error => {
                console.error("Errore AJAX:", error);
                alert("Errore nell'aggiunta al carrello.");
            });
        });

        form.setAttribute('data-ajax-ready', 'true');
    });
});