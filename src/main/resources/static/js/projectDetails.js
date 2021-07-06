let projectModal = document.getElementById('projectModal')
projectModal.addEventListener('show.bs.modal', function (event) {

// Trigger of the modal
    let project = event.relatedTarget

// Extract info from data-bs-* attributes and update the modal's content.
    projectModal.querySelector('.modal-projectName').textContent = project.getAttribute('data-bs-projectName')
    projectModal.querySelector('.modal-projectDescription').textContent = project.getAttribute('data-bs-projectDescription')
    projectModal.querySelector('.modal-projectMembers').textContent = project.getAttribute('data-bs-projectMembers')
    projectModal.querySelector('.modal-projectStartDate').textContent = project.getAttribute('data-bs-projectStartDate')
    projectModal.querySelector('.modal-projectEndDate').textContent = project.getAttribute('data-bs-projectEndDate')
})