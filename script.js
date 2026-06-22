const idField = document.getElementById('id');
const nomeField = document.getElementById('nome');
const emailField = document.getElementById('email');
const addButton = document.getElementById('btn-add');
const updateButton = document.getElementById('btn-update');
const deleteButton = document.getElementById('btn-delete');
const clearButton = document.getElementById('btn-clear');
const userTable = document.getElementById('user-table').querySelector('tbody');

let selectedRowIndex = -1;

function limparCampos() {
  idField.value = '';
  nomeField.value = '';
  emailField.value = '';
  selectedRowIndex = -1;
  clearSelection();
}

function clearSelection() {
  const rows = userTable.querySelectorAll('tr');
  rows.forEach((row) => row.classList.remove('selected'));
}

function validarCampos() {
  return idField.value.trim() !== '' && nomeField.value.trim() !== '' && emailField.value.trim() !== '';
}

function adicionarUsuario() {
  if (!validarCampos()) {
    alert('Preencha todos os campos.');
    return;
  }

  const id = idField.value.trim();
  const nome = nomeField.value.trim();
  const email = emailField.value.trim();

  const rows = Array.from(userTable.querySelectorAll('tr'));
  const existing = rows.some((row) => row.children[0].textContent === id);
  if (existing) {
    alert('ID já existe.');
    return;
  }

  const row = document.createElement('tr');
  row.innerHTML = `<td>${id}</td><td>${nome}</td><td>${email}</td>`;
  row.addEventListener('click', () => selecionarLinha(row));
  userTable.appendChild(row);
  limparCampos();
}

function selecionarLinha(row) {
  clearSelection();
  row.classList.add('selected');
  selectedRowIndex = Array.from(userTable.children).indexOf(row);

  idField.value = row.children[0].textContent;
  nomeField.value = row.children[1].textContent;
  emailField.value = row.children[2].textContent;
}

function atualizarUsuario() {
  if (selectedRowIndex < 0) {
    alert('Selecione um usuário para atualizar.');
    return;
  }
  if (!validarCampos()) {
    alert('Preencha todos os campos.');
    return;
  }

  const id = idField.value.trim();
  const nome = nomeField.value.trim();
  const email = emailField.value.trim();

  const rows = Array.from(userTable.querySelectorAll('tr'));
  const duplicate = rows.some((row, index) => index !== selectedRowIndex && row.children[0].textContent === id);
  if (duplicate) {
    alert('ID já existe em outro usuário.');
    return;
  }

  const row = rows[selectedRowIndex];
  row.children[0].textContent = id;
  row.children[1].textContent = nome;
  row.children[2].textContent = email;
  limparCampos();
}

function excluirUsuario() {
  if (selectedRowIndex < 0) {
    alert('Selecione um usuário para excluir.');
    return;
  }

  const rows = Array.from(userTable.querySelectorAll('tr'));
  const row = rows[selectedRowIndex];
  if (row) {
    row.remove();
  }
  limparCampos();
}

userTable.querySelectorAll('tr').forEach((row) => {
  row.addEventListener('click', () => selecionarLinha(row));
});

addButton.addEventListener('click', adicionarUsuario);
updateButton.addEventListener('click', atualizarUsuario);
deleteButton.addEventListener('click', excluirUsuario);
clearButton.addEventListener('click', limparCampos);
