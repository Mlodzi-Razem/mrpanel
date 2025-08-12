'use server';

export const enum HttpMethod {
    GET = 'GET',
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE',
    PATCH = 'PATCH'
}

export interface ApiCallRequest {
    url: string;
    method: HttpMethod;
    body?: object;
    headers?: Headers;
    searchParams?: URLSearchParams;
}

function createHeaders(request: ApiCallRequest) {
    const headers = new Headers({
        'Content-Type': 'application/json'
    });

    [...(request.headers?.entries() ?? [])].forEach(([header, value]) => {
        headers.set(header, value);
    })

    return headers;
}

function createUrl(request: ApiCallRequest) {
    const urlPrefix = process.env.API_URL_PREFIX;
    const urlWithoutLeadingSlashes = request.url.replace(/^\/+/, '');

    const url = new URL(urlPrefix + '/' + urlWithoutLeadingSlashes);
    if (request.searchParams) {
        url.search = request.searchParams.toString();
    }
    return url;
}

function createBody(request: ApiCallRequest) {
    if (request.body === undefined) {
        return undefined;
    }

    return (request.body instanceof Array || typeof request.body === 'object')
        ? JSON.stringify(request.body)
        : request.body;
}

export async function apiCall(request: ApiCallRequest): Promise<Response> {
    const url = createUrl(request);
    const headers = createHeaders(request);
    const body = createBody(request);

    return fetch(
        url,
        {
            method: request.method,
            body,
            headers
        }
    )
}