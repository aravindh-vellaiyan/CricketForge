async function api(path, method = 'GET', body = null) {
    const options = { method, headers: {} };

    if (body) {
        options.headers['Content-Type'] = 'application/json';
        options.body = JSON.stringify(body);
    }

    let res;
    try {
        res = await fetch(path, options);
    } catch (e) {
        throw { message: "Network error. Backend might be down." };
    }

    // Parse JSON safely
    let json = null;
    try {
        json = await res.clone().json();
    } catch (_) {
        console.log("Invalid JSON Response" + res.clone())
    }

    if (!res.ok) {
        const msg =
            json?.message ||
            json?.error ||
            json?.status ||
            res.statusText ||
            "Unknown error";

        throw { message: msg, status: res.status, raw: json };
    }

    return json;
}

function showError(msg) {
    const el = document.getElementById("error");
    if (el) el.innerText = msg;
}
async function logout() {
    try {
        await api('/auth/logout', 'POST');

    } catch (err) {
        // Even if backend fails, still clear cookie
        console.warn("Logout error:", err.message);
    }

    // Ensure cookie is gone on client
    document.cookie = "SESSION_ID=; Max-Age=0; path=/";

    window.location = "index.html";
}