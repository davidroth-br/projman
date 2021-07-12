let phone = document.getElementById('phone');
phone.addEventListener('keyup', (e) => {
    if (e.key !== "Delete" && e.key !== "Backspace") {
        let phoneValue = phone.value.replace(/[^\d]/gi, "");
        if (phoneValue.length <= 3) {
            phone.value = phoneValue;
        } else if (phoneValue.length < 7) {
            phone.value = "(" + phoneValue.substring(0, 3) + ") " + phoneValue.substring(3);
        } else {
            phone.value = ("(" + phoneValue.substring(0, 3) + ") " + phoneValue.substring(3, 6) + "-" + phoneValue.substring(6)).substring(0, 14);
        }
    }
});
