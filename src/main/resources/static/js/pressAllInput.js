function pressAll() {
    isAll = !isAll
    const items = document.querySelectorAll('input');
    items.forEach(b => b.checked = isAll)
    pressAllBut.textContent = isAll ? "Вычеркнуть всё" : "Выделить всё"
}

let isAll = false;
const pressAllBut = document.getElementById("pressAllButton")
pressAllBut.onclick = pressAll