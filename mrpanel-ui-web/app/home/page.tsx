import Panel from "@/components/layout/panel/Panel";
import Button from "@/components/form/button/Button";

export default function Home() {
    return <div style={{display: 'flex', justifyContent: 'center'}}>
        <Panel style={{width: '20rem', height: '30rem', display: 'flex', flexDirection: 'column', gap: '0.5rem', justifyContent: 'space-between'}}>
            <h3>Wielki test UI</h3>
            <p>Lorem ipsum</p>
            <Button>Test</Button>
        </Panel>
    </div>
}