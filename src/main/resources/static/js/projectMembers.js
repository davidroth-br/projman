let membersModal = document.getElementById('membersModal')
membersModal.addEventListener('show.bs.modal', function (event) {

// Button that trigger of the modal
    let members = event.relatedTarget

// Extract info from data-bs-* attributes and update the modal's content.
    membersModal.querySelector('.modal-projectName').textContent = members.getAttribute('data-bs-projectName')
    membersModal.querySelector('.modal-projectLeader').textContent = members.getAttribute('data-bs-projectLeader')
    membersModal.querySelector('.modal-projectMembers').textContent = members.getAttribute('data-bs-projectMembers')
})