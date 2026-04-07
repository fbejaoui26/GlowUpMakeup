document.addEventListener("DOMContentLoaded", function() {
    
    // seleziono tutti gli input della quantità nel carrello
    const quantityInputs = document.querySelectorAll('.auto-submit');
    
    // per ognuno di essi, aggiungo in ascolto l'evento 'change'
    quantityInputs.forEach(input => {
        input.addEventListener('change', function() {
            this.closest('form').submit();
        });
    });

});