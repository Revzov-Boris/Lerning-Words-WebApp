function pressAll() {
    const items = document.querySelectorAll('input');
    items.forEach(b => b.checked = true)
}

const pressAllBut = document.getElementById("pressAllButton")
pressAllBut.onclick = pressAll