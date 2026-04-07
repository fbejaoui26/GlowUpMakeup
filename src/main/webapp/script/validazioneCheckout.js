document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("formCheckout");
    if (!form) return;

    const indirizzo = document.getElementById("indirizzo");
    const citta = document.getElementById("citta");
    const cap = document.getElementById("cap");
    const carta = document.getElementById("carta");

    // espressioni regolari
    const regexIndirizzo = /^[a-zA-Z0-9\s,.'-]{5,100}$/;
    const regexCitta = /^[a-zA-Z\sèéàòìù']{2,50}$/;
    const regexCap = /^\d{5}$/; 
    const regexCarta = /^\d{16}$/; 

    function validaCampo(input, regex, idErrore, messaggio) {
        const spanErrore = document.getElementById(idErrore);
        if (!regex.test(input.value.trim())) {
            spanErrore.textContent = messaggio;
            input.classList.add("errore-input");
            return false;
        } else {
            spanErrore.textContent = "";
            input.classList.remove("errore-input");
            return true;
        }
    }

    // validazione live
    indirizzo.addEventListener("blur", () => validaCampo(indirizzo, regexIndirizzo, "erroreIndirizzo", "Inserisci un indirizzo valido."));
    citta.addEventListener("blur", () => validaCampo(citta, regexCitta, "erroreCitta", "Inserisci una città valida."));
    cap.addEventListener("blur", () => validaCampo(cap, regexCap, "erroreCap", "Il CAP deve contenere 5 numeri."));
    carta.addEventListener("blur", () => validaCampo(carta, regexCarta, "erroreCarta", "Inserisci le 16 cifre della carta."));

    // validazione submit
    form.addEventListener("submit", function(event) {
        const isIndirizzoValido = validaCampo(indirizzo, regexIndirizzo, "erroreIndirizzo", "Inserisci un indirizzo valido.");
        const isCittaValido = validaCampo(citta, regexCitta, "erroreCitta", "Inserisci una città valida.");
        const isCapValido = validaCampo(cap, regexCap, "erroreCap", "Il CAP deve contenere 5 numeri.");
        const isCartaValida = validaCampo(carta, regexCarta, "erroreCarta", "Inserisci le 16 cifre della carta.");

        if (!isIndirizzoValido || !isCittaValido || !isCapValido || !isCartaValida) {
            event.preventDefault(); // blocca l'invio
        }
    });
});