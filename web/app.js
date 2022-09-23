const element = document.querySelector("safehello-host");

element.addEventListener("eventChanged", (ev) => {
  // Handle event updates.
  console.log("eventChanged", ev);
});
