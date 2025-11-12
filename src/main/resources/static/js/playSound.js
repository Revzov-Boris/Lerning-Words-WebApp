function playAudio(audioId) {
    const audio = document.getElementById(audioId);
    if (audio) {
      audio.play().catch(e => console.log('Audio play failed:', e));
    }
}