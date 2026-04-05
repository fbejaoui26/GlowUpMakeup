// aspetto che tutta la pagina HTML sia caricata prima di eseguire lo script
document.addEventListener("DOMContentLoaded", function() {
    
    // recupero il form e i vari campi tramite il loro ID
    const form = document.getElementById("formRegistrazione");
    if (!form) return; 

    const nome = document.getElementById("nome");
    const cognome = document.getElementById("cognome");
    const email = document.getElementById("email");
    const password = document.getElementById("password");

    // ESPRESSIONI REGOLARI 
    const regexNome = /^[a-zA-Z\sèéàòìù']{2,30}$/; 
  
    const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d\w\W]{8,}$/;

    
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

    // (evento blur: scatta quando l'utente esce dal campo)
    nome.addEventListener("blur", () => validaCampo(nome, regexNome, "erroreNome", "Inserisci un nome valido (minimo 2 lettere, solo caratteri)."));
    cognome.addEventListener("blur", () => validaCampo(cognome, regexNome, "erroreCognome", "Inserisci un cognome valido."));
    email.addEventListener("blur", () => validaCampo(email, regexEmail, "erroreEmail", "Inserisci un'email valida (es. nome@dominio.com)."));
    password.addEventListener("blur", () => validaCampo(password, regexPassword, "errorePassword", "La password deve avere almeno 8 caratteri, una maiuscola e un numero."));

    // evento submit: scatta quando si preme il bottone
    form.addEventListener("submit", function(event) {
        // rieseguo tutti i controlli insieme
        const isNomeValido = validaCampo(nome, regexNome, "erroreNome", "Inserisci un nome valido.");
        const isCognomeValido = validaCampo(cognome, regexNome, "erroreCognome", "Inserisci un cognome valido.");
        const isEmailValido = validaCampo(email, regexEmail, "erroreEmail", "Inserisci un'email valida.");
        const isPasswordValido = validaCampo(password, regexPassword, "errorePassword", "Password debole (min. 8 caratteri, 1 maiusc, 1 numero).");

        // se anche solo uno dei campi è falso, blocco la partenza dei dati verso il server
        if (!isNomeValido || !isCognomeValido || !isEmailValido || !isPasswordValido) {
            event.preventDefault(); 
        }
    });
});