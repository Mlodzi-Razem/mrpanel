import {collectDefaultMetrics, register} from "prom-client";
import {NextResponse} from "next/server";

function registerMetricsIfNeeded() {
    if (!('metricsRegistered' in globalThis)) {
        const prefix = 'mrpanel_ui_web';
        collectDefaultMetrics({prefix});
        (globalThis as any).metricsRegistered = true;
    }
}

registerMetricsIfNeeded();

export async function GET() {
    return new NextResponse(
        await register.metrics(),
        {
            headers: {
                'Content-Type': register.contentType
            }
        });
}