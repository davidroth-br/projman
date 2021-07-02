let taskModal = document.getElementById('taskModal')
taskModal.addEventListener('show.bs.modal', function (event) {

// Button that trigger of the modal
    let task = event.relatedTarget

// Extract info from data-bs-* attributes and update the modal's content.
    taskModal.querySelector('.modal-taskName').textContent = task.getAttribute('data-bs-taskName')
    taskModal.querySelector('.modal-taskDescription').textContent = task.getAttribute('data-bs-taskDescription')
    taskModal.querySelector('.modal-taskDeadline').textContent = task.getAttribute('data-bs-taskDeadline')
    taskModal.querySelector('.modal-taskCompletionDate').textContent = task.getAttribute('data-bs-taskCompletionDate')
})