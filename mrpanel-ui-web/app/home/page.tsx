import Panel from "@/components/layout/panel/Panel";
import Button from "@/components/form/button/Button";
import Page from "@/components/layout/page/Page";

export default function Home() {
    return <Page>
        <Panel shadow style={{width: '20rem', height: '30rem', display: 'flex', flexDirection: 'column', gap: '0.5rem', justifyContent: 'space-between', alignSelf: 'center'}}>
            <h3>Wielki test UI</h3>
            <p>Lorem ipsum</p>
            <Button>Test</Button>
        </Panel>
    </Page>
}