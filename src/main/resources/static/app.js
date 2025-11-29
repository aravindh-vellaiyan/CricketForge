async function api(url, method = "GET", body = null) {
    const options = {
        method,
        headers: { "Content-Type": "application/json" },
        credentials: "include" // IMPORTANT: send cookies
    };

    if (body) options.body = JSON.stringify(body);

    const res = await fetch(url, options);

    if (!res.ok) {
        const text = await res.text();
        throw new Error(text);
    }

    return res.json();
}

function showError(id, msg) {
    document.getElementById(id).innerText = msg;
}

function redirect(page) {
    window.location.href = page;
}