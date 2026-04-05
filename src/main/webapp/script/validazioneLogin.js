document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("formLogin");
    if (!form) return;

    const email = document.getElementById("emailLogin");
    const password = document.getElementById("passwordLogin");

    const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

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

    // validazione Live (blur)
    email.addEventListener("blur", () => validaCampo(email, regexEmail, "erroreEmailLogin", "Inserisci un'email valida."));
    
    // per la password al login basta controllare che non sia vuota
    password.addEventListener("blur", function() {
        const spanErrore = document.getElementById("errorePasswordLogin");
        if (password.value.trim() === "") {
            spanErrore.textContent = "La password è obbligatoria.";
            password.classList.add("errore-input");
        } else {
            spanErrore.textContent = "";
            password.classList.remove("errore-input");
        }
    });

    // validazione al submit
    form.addEventListener("submit", function(event) {
        const isEmailValida = validaCampo(email, regexEmail, "erroreEmailLogin", "Inserisci un'email valida.");
        let isPasswordValida = true;

        if (password.value.trim() === "") {
            document.getElementById("errorePasswordLogin").textContent = "La password è obbligatoria.";
            password.classList.add("errore-input");
            isPasswordValida = false;
        }

        // blocca l'invio al server se i controlli falliscono
        if (!isEmailValida || !isPasswordValida) {
            event.preventDefault();
        }
    });
});