<script>
    import {Button, Checkbox, Input, Label, Modal} from "flowbite-svelte";

    let formModal1 = false;

    let loginFormData = {
        username: '',
        password: ''
    }
    const loginSubmit = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/member/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify(loginFormData)
            });
            if (response.ok) {
                const data = await response.json()
                // console.log(data)
                if (data.resultCode === 'S-2') {
                    window.location.href = '/';
                    alert('로그인이 완료되었습니다.')
                    formModal1 = false
                } else {
                    const errorMessage = data.errorMessage;
                    console.error('로그인 실패:', errorMessage)
                }
            } else {
                console.error('서버 응답 오류1:', response.statusText)
                alert('다시 입력해주세요.')
                return;
            }
        } catch (error) {
            console.error('오류 발생1:', error)
        }
    }
    export let accessToken;
    export let refreshToken;
    export async function load({ url }) {
        const searchParams = url.searchParams;
        const accessToken = searchParams.get('access_token');
        const refreshToken = searchParams.get('refresh_token');

        console.log(accessToken)
        console.log(refreshToken)

        return {
            props: {
                accessToken,
                refreshToken,
            },
        };
    }

</script>

<Button on:click={() => (formModal1 = true)} color="light">로그인</Button>
<a href="http://localhost:8080/oauth2/authorization/kakao?redirect_uri=http://localhost:5173&mode=login">
    <Button color="yellow">카카오 로그인</Button>
</a>
<a href="http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:5173&mode=login">
    <Button color="blue">구글 로그인</Button>
</a>
<a href="http://localhost:8080/oauth2/authorization/naver?redirect_uri=http://localhost:5173&mode=login">
    <Button color="green">네이버 로그인</Button>
</a>
<a href="http://localhost:8080/oauth2/authorization/kakao?redirect_uri=http://localhost:3000&mode=unlink">
    <Button color="yellow">카카오 로그아웃</Button>
</a>
<a href="http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000&mode=unlink">
    <Button color="blue">구글 로그아웃</Button>
</a>
<a href="http://localhost:8080/oauth2/authorization/naver?redirect_uri=http://localhost:3000&mode=unlink">
    <Button color="green">네이버 로그아웃</Button>
</a>
<!--로그인 창-->

<Modal bind:open={formModal1} size="xs" autoclose={false} class="w-full">
    <form class="flex flex-col space-y-6" on:submit|preventDefault={loginSubmit}>
        <h3 class="mb-4 text-xl font-medium text-gray-900 dark:text-white">로그인</h3>
        <Label class="space-y-2">
            <span>아이디</span>
            <Input type="username" bind:value={loginFormData.username} placeholder="홍길순" required/>
        </Label>
        <Label class="space-y-2">
            <span>비밀번호</span>
            <Input type="password" bind:value={loginFormData.password} placeholder="•••••" required/>
        </Label>
        <div class="flex items-start">
            <Checkbox>아이디 / 비밀번호 기억하기</Checkbox>
            <a href="/" class="ms-auto text-sm text-primary-700 hover:underline dark:text-primary-500"> 비번 잃어버림? </a>
        </div>
        <Button type="submit" class="w-full1">로그인 클릭 좀</Button>
    </form>
</Modal>