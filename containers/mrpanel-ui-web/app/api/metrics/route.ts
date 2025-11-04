import {collectDefaultMetrics, register} from "prom-client";
import {NextRequest, NextResponse} from "next/server";

function registerMetricsIfNeeded() {
    if (!('metricsRegistered' in globalThis)) {
        const prefix = 'mrpanel_ui_web';
        collectDefaultMetrics({prefix});
        (globalThis as unknown as { metricsRegistered: boolean }).metricsRegistered = true;
    }
}

registerMetricsIfNeeded();

async function createResponse() {
    const metrics = await register.metrics();
    const headers = {
        'Content-Type': register.contentType
    };

    return new NextResponse(metrics, {headers});
}

const UNAUTHORIZED_BODY = JSON.stringify({message: "Unauthorized"});
const NO_TOKEN_BODY = JSON.stringify({message: 'Metrics token not configured'});

const UNAUTHORIZED = new NextResponse(UNAUTHORIZED_BODY, {status: 401});
const NO_TOKEN = new NextResponse(NO_TOKEN_BODY, {status: 500});

function validateRequest(req: NextRequest) {
    const authHeader = req.headers.get('Authorization');
    if (!authHeader) {
        throw UNAUTHORIZED;
    }

    const expectedToken = process.env.METRICS_TOKEN;
    if (!expectedToken) {
        throw NO_TOKEN;
    }

    const tokensMatch = authHeader.trim() === `Bearer ${expectedToken}`.trim();
    if (!tokensMatch) {
        throw UNAUTHORIZED;
    }
}

/**
 * This endpoint exposes metrics for Prometheus.
 *
 * The endpoint is protected by a token to prevent unauthorized access.
 * The token should be regenerated on every deployment.
 */
export async function GET(req: NextRequest) {
    try {
        validateRequest(req);
        return await createResponse();
    } catch (e) {
        console.error("Error while handling metrics request", {url: req.url, headers: req.headers});

        if (e instanceof NextResponse) {
            return e;
        }

        throw e;
    }
}