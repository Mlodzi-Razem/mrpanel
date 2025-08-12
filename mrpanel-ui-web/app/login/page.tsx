import { redirect } from "next/navigation";
import Button from "@/components/form/button/Button";
import Panel from "@/components/layout/panel/Panel";

export default function LoginPage() {
    const isLoggedIn = false; //TODO: Implement

    if (isLoggedIn) {
        redirect('/home');
    }

    return <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh'}}>
        <Panel style={{width: '20rem', height: '30rem'}}>
            <div style={{display: 'flex', flexDirection: 'column', gap: '0.5rem', justifyContent: 'space-between', height: '100%'}}>
                <h3>Test</h3>
                <div style={{flex: 1, backgroundColor: 'var(--color-background)', borderRadius: 'var(--border-radius)', color: 'var(--color-copy-lighter)', padding: '0.25rem'}}>
                    PLACEHOLDER
                </div>
                <div style={{display: 'flex', flexDirection: 'row', gap: '0.5rem'}}>
                    <Button flavor="primary">Primary</Button>
                    <Button flavor="secondary">Secondary</Button>
                </div>
            </div>
        </Panel>
    </div>;
}