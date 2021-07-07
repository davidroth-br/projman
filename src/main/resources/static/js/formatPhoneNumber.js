let phone = document.getElementById('phone');
phone.addEventListener('keyup', function(){
    let phoneValue = phone.value;
    let output;
    phoneValue = phoneValue.replace(/[^0-9]/g, '');
    let area = phoneValue.substr(0, 3);
    let pre = phoneValue.substr(3, 3);
    let tel = phoneValue.substr(6, 4);
    if (area.length < 3) {
        output = "(" + area;
    } else if (area.length === 3 && pre.length < 3) {
        output = "(" + area + ")" + " " + pre;
    } else if (area.length === 3 && pre.length === 3) {
        output = "(" + area + ")" + " " + pre + "-"+tel;
    }
    phone.value = output;
});