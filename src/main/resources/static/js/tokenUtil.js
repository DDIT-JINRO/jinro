async function refreshAccessToken() {
  const res = await fetch('/refreshToken', {
    method: 'POST',
    credentials: 'include'
  });

  if (!res.ok) throw new Error('Refresh token invalid');
  return res.json(); 
}

async function fetchWithRefresh(url, options = {}) {
  options.credentials = 'include';

  let response = await fetch(url, options);

  if (response.status === 401) {
    try {
      await refreshAccessToken();

      response = await fetch(url, options);
    } catch (err) {
      console.error('리프레시 실패:', err);
      throw err;
    }
  }

  return response;
}
