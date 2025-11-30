async function api(url, method = "GET", body = null) {
    const options = {
        method,
        headers: { "Content-Type": "application/json" },
        credentials: "include"
    };

    if (body) options.body = JSON.stringify(body);

    const res = await fetch(url, options);

    if (!res.ok) {
        let errorBody = await res.text();
        let parsed;

        try {
            parsed = JSON.parse(errorBody); // backend error JSON
        } catch (e) {
            throw new Error(errorBody); // plain text fallback
        }

        throw parsed;  // <<=== THROW THE OBJECT, not Error()
    }

    return res.json();
}
function clearErrorOnInput(errorId) {
    const errorEl = document.getElementById(errorId);
    document.querySelectorAll("input, textarea, select").forEach(el => {
        el.addEventListener("input", () => {
            if (errorEl) errorEl.innerText = "";
        });
    });
}

function showError(id, msg) {
    document.getElementById(id).innerText = msg;
}

function redirect(page) {
    window.location.href = page;
}