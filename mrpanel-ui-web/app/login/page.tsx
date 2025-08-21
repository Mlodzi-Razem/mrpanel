import { redirect } from "next/navigation";
import Button from "@/components/form/button/Button";
import Panel from "@/components/layout/panel/Panel";
//import {mockSession} from "next-auth/client/__tests__/helpers/mocks";
//import user = mockSession.user;
import {authOptions} from "@/app/api/auth/[...nextauth]/route";
import {getServerSession} from "next-auth";
import {isNotNull} from "effect/Predicate";
let isLoggedIn = false; //TODO: KInda done

async function getSession() {
    const session = await getServerSession(authOptions);
    if (isNotNull(session)) {
        console.log(session);
    }
    return session;
}

export default async function LoginPage() {
    const session = await getSession();
    if (session && session.user) {
        isLoggedIn = true;
        redirect('/home'); // User wyjebany do home jesli jest zalogowany, small catch, jesli
        // user dopisze sobie .user wlasciwosc to wchodzi do home, TO BE FIXED LATER
    }

    return <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh'}}>
        <Panel style={{width: '20rem', height: '30rem'}}>
            <div style={{display: 'flex', flexDirection: 'column', gap: '0.5rem', justifyContent: 'space-between', height: '100%'}}>
                <h3>Test</h3>
                <div style={{flex: 1, backgroundColor: 'var(--color-background)', borderRadius: 'var(--border-radius)', color: 'var(--color-copy-lighter)', padding: '0.25rem'}}>

                </div>
                <div style={{display: 'flex', flexDirection: 'row', gap: '0.5rem'}}>
                    <Button flavor="primary">Primary</Button>
                    <Button flavor="secondary">Secondary</Button>
                </div>
            </div>
        </Panel>
    </div>;
}