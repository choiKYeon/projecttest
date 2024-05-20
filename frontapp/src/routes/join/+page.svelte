<script>
    import {Input, Label, Helper, Button, Checkbox, A, Modal} from 'flowbite-svelte';

    let formData = {
        name: '',
        username: '',
        password: '',
        email: ''
    };
    let formModal = false;

    const joinSubmit = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/member/join', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                const data = await response.json()
                if (data.resultCode === 'S-1') {
                    window.location.href = '/login';
                    alert('회원가입이 완료되었습니다. 로그인을 진행해주세요.')
                    formModal = false
                } else {
                    const errorMessage = data.errorMessage;
                    console.error('가입  실패:', errorMessage)
                }
            } else {
                console.error('서버 응답 오류:', response.statusText)
                alert('다시 입력해주세요.')
                return;
            }
        } catch (error) {
            console.error('오류 발생:', error)
        }
    }

</script>

<!--회원가입 창-->
<Button on:click={() => (formModal = true)} color="red">회원가입</Button>

<Modal bind:open={formModal} size="xs" autoclose={false} class="w-full">
    <form class="flex flex-col space-y-6" on:submit|preventDefault={joinSubmit}>
        <h3 class="mb-4 text-xl font-medium text-gray-900 dark:text-white">간편 회원가입</h3>
        <Label class="space-y-2">
            <span>이름</span>
            <Input type="name" bind:value={formData.name} placeholder="홍길동" required />
        </Label>
        <Label class="space-y-2">
            <span>아이디</span>
            <Input type="username" bind:value={formData.username} placeholder="user" required />
        </Label>
        <Label class="space-y-2">
            <span>이메일</span>
            <Input type="email" bind:value={formData.email} placeholder="name@company.com" required />
        </Label>
        <Label class="space-y-2">
            <span>비밀번호</span>
            <Input type="password" bind:value={formData.password} placeholder="•••••" required />
        </Label>
        <div class="flex items-start">
            <Checkbox>이용약관 동의(필수)</Checkbox>
            <!--            <a href="/" class="ms-auto text-sm text-primary-700 hover:underline dark:text-primary-500"> Lost password? </a>-->
        </div>
        <Button type="submit" class="w-full">회원 가입</Button>
        <!--        <Button type="submit" class="w-1/2 mx-auto" color="yellow">카카오로 로그인</Button>-->
        <!--        <Button type="submit" class="w-1/2 mx-auto" color="blue">구글로 로그인</Button>-->
        <!--        <Button type="submit" class="w-1/2 mx-auto" color="green">네이버로 로그인</Button>-->
    </form>
</Modal>
