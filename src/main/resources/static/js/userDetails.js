let userModal = document.getElementById('userModal')
userModal.addEventListener('show.bs.modal', function (event) {

// Button that trigger of the modal
    let user = event.relatedTarget

// Extract info from data-bs-* attributes and update the modal's content.
    userModal.querySelector('.modal-userFullName').textContent = user.getAttribute('data-bs-userFullName')
    userModal.querySelector('.modal-userEmail').textContent = user.getAttribute('data-bs-userEmail')
    userModal.querySelector('.modal-userPhone').textContent = user.getAttribute('data-bs-userPhone')
})